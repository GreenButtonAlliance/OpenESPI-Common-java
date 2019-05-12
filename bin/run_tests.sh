#!/bin/sh
mvn clean -DskipTests=true package cobertura:cobertura
