#!/bin/bash

DIR=$(dirname "$0")

kubectl apply -f $DIR/backend.deployment.yaml
kubectl apply -f $DIR/frontend.deployment.yaml
kubectl expose deployment avocado-backend --port 80 --type LoadBalancer
kubectl expose deployment avocado-frontend --port 80 --type LoadBalancer

svc=avocado-backend
external_ip=""
while [ -z $external_ip ]; do
  echo "Waiting for public ip for service $svc..."
  external_ip=$(kubectl get svc $svc --template="{{range .status.loadBalancer.ingress}}{{.ip}}{{end}}")
  [ -z "$external_ip" ] && sleep 10
done
echo "Service $svc recieved public ip: $external_ip"

kubectl set env deployment/avocado-frontend BACKEND_ROOT="http://$external_ip"

svc=avocado-frontend
while [ -z $external_ip ]; do
  echo "Waiting for public ip for service $svc..."
  external_ip=$(kubectl get svc $svc --template="{{range .status.loadBalancer.ingress}}{{.ip}}{{end}}")
  [ -z "$external_ip" ] && sleep 10
done
echo "Avocado Manager installed. Open: http://$external_ip"