#!/bin/bash
bash -i -c "$1 $2 $3 $4 s_client -connect $5 2>&1 | sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > $6"
