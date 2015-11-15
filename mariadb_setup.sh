#!/bin/bash

if [ ! $# -eq 0 ]; then

    if [ $1 == "-i" ]; then

        mysql -u root -p"" < mariadb_setup_user.cmd < mariadb_setup_db.cmd
        #mysql -u root -p"" < mariadb_setup_db.cmd

    fi

    if [ $1 == "-u" ]; then

        mysql -u root -p"" < mariadb_setup_db.cmd

    fi

else

    echo "use -i for initial setup, -u to update setup"

fi

