#!/bin/bash

mvn clean install

docker build -t web_crawler -f Dockerfile .

docker run -p 8080:8080 web_crawler
