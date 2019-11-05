#!/bin/bash

DIR=$(dirname "$0")
kubectl delete svc avocado-backend avocado-frontend
kubectl delete -f $DIR/backend.deployment.yaml
kubectl delete -f $DIRfrontend.deployment.yaml