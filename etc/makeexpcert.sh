#!/usr/bin/env bash
openssl req \
  -x509 -nodes -days 1 \
  -subj '/C=US/ST=Oregon/L=Portland/CN=127.0.0.1' \
  -newkey rsa:2048 -keyout mycert.pem -out mycert.pem
