#!/bin/bash

mvn clean package && docker build -t beer-catalogue .