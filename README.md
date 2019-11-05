# &#129361; Avocado Manager 2019 &#129361;
Manage your avocados like it's 2019.

And learn GKE and Google Cloud Build while you're at it.

## Architecture
The application consists of two components:

* backend
  * Java 8/Quarkus/MicroProfile application
  * exposes one rest endpoint `/avocados` which returns a list of JSON objects
* frontend
  * JS/React/NPM/Nginx front end
  * uses the backend service to populate a list of avocados to display


## Setting up Cloud Build for images with triggers
The project is build using Google Cloud Build:
https://cloud.google.com/cloud-build/

The steps are defined in `cloudbuild.yaml` files for each subproject:

* backend
  * cloudbuild: https://github.com/blofroth/avocado-manager/blob/master/backend/cloudbuild.yaml
  * Dockerfile: https://github.com/blofroth/avocado-manager/blob/master/backend/src/main/docker/Dockerfile.jvm
* frontend
  * cloudbuild: https://github.com/blofroth/avocado-manager/blob/master/frontend/cloudbuild.yaml
  * Dockerfile: https://github.com/blofroth/avocado-manager/blob/master/frontend/Dockerfile

To set up triggers to build on every commit, go to build trigger configuration in the GCP webconsole:
https://console.cloud.google.com/cloud-build/triggers

* First you need to connect your repository to GCP Cloud build
* Then add the two triggers as below:
    * ![Backend trigger](/docs/trigger-backend.png)
    * ![Frontend trigger](/docs/trigger-frontend.png)

## Deploying manually into a GKE cluster

1. Set up a GKE cluster, e.g. following this [tutorial](https://cloud.google.com/kubernetes-engine/docs/quickstart)
    * Make sure `kubectl` is set up towards a working cluster after
2. Set an environment variable to your current GCP project
    * `export PROJECT_ID=...`
3. Deploy and expose the backend microservice:
    * `kubectl run avocado-backend --image=gcr.io/$PROJECT_ID/avocado-manager-backend`
    * `kubectl expose deployment avocado-backend --port 80 --type LoadBalancer`
4. Write down the public IP of the service, waiting until it gets assigned:
    * `kubectl get svc -w`
    * `export BACKEND_HOST=...`
5. Deploy and expose the frontend microservice:
    * `kubectl run avocado-frontend --image=gcr.io/$PROJECT_ID/avocado-manager-frontend`
6. Edit the frontend deployment:

    * `kubectl edit deployment avocado-frontend`
    * Add the environment config just below `image: ...`, substituting for your actual IP noted in previous steps:
    ```
    env:
    - name: BACKEND_ROOT
      value: "http://35.228.235.121"
    ```
7. Wait for the frontend to automatically get redeployed when saving the file
8. Check the public IP of the frontend:
  * `kubectl get svc -w`
9. Open the IP in a browser, and voilà, avocados!

## Deploying into a GKE cluste with a script 

Install:
`sh deployment/install.sh`

This will wait until both services receive public IP:s.

Uninstall:
`sh deployment/uninstall.sh`

## GitOps deploys with triggers
Configure these triggers in Cloud Build:

| Trigger name              | Type          | Regex                           | Build file                      |
| -------------             | ------------- | -----                           | ------                          |
| deploy-avocado-frontend   | Push to tag   | frontend-[0-9]+\.[0-9]+\.[0-9]+ | deploy-frontend.cloudbuild.yaml | 
| deploy-avocado-backend    | Push to tag   | backend-[0-9]+\.[0-9]+\.[0-9]+  | deploy-backend.cloudbuild.yaml 	|
				
To trigger a deploy of the backend service in the currently checked out commit, with tag 1.0.0:

`TAG=1.0.0; git tag backend-$TAG; git push origin backend-$TAG`

To trigger a deploy of the frontend service in the currently checked out commit, with tag 1.0.0:
`TAG=1.0.0; git tag frontend-$TAG; git push origin frontend-$TAG`

Some limitations and quirks:
* Any new tag independent of version sequence would trigger a new deploy in that tag
* If you change the deploy-cloudbuild files, there would not exist a matching image in that commit, since they are only built on changes to `backend/**` or `frontend/**` files. Simplest solution is to make dummy commit in the relevant subfolder. Perhaps a `bump-after` file where you put the commit hash of the change to the cloudbuild file.
* Rule of thumb: tag `backend-...` only for commits that touch `backend/**` and vice versa for frontend.