#!/bin/bash

kubectl delete svc avocado-backend avocado-frontend
kubectl delete -f backend.deployment.yaml
kubectl delete -f frontend.deployment.yaml