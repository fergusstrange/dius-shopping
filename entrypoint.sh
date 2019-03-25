#!/bin/bash

set -e

if [ "$#" -eq 0 ]; then
    exec java -jar /application.jar
else
    exec "$@"
fi
