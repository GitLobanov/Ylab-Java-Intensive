#!/bin/bash
echo "Running init script..."
echo "host all all 0.0.0.0/0 md5" >> /var/lib/postgresql/data/pg_hba.conf
echo "Init script completed."