#!/bin/bash
###########################################################################
# COPYRIGHT Ericsson 2017
#
# The copyright to the computer program(s) herein is the property of
# Ericsson Inc. The programs may be used and/or copied only with written
# permission from Ericsson Inc. or in accordance with the terms and
# conditions stipulated in the agreement/contract under which the
# program(s) have been supplied.
# This script requires bash 4 or above
# $Date: 2017-08-31
#
# This script will copy updatePIB.sh to /ericsson/tor/data/fls/script/.
###########################################################################

# Source jboss logger methods
source /ericsson/3pp/jboss/bin/jbosslogger

# ReadOnly variables
readonly MOUNT_DIR="/ericsson/tor/data"
readonly DEST_DIR="$MOUNT_DIR/fls/script"
readonly SRC_DIR="/ericsson/fls/bin"
readonly SRC_FILE="$SRC_DIR/updatePIB.sh"

# Function check if directory mounted and trigger copyfile
function moveScriptLocation()
{
if [ -d "$MOUNT_DIR" -a "$(stat -f -L -c %T $MOUNT_DIR)" == "nfs" ]
then
        checkOrCreateScriptDir
        if [ "$?" -eq 0 ]; then
            copyFile;
        fi
else
    error "Mount directory $MOUNT_DIR does not exist"
    exit 1;
fi
}


# Function to check if script dir exist else create it
function checkOrCreateScriptDir()
{
    if [  ! -d "$DEST_DIR" ]; then
        if mkdir -p -m 755 $DEST_DIR; then
            return 0
        else
            error "Failed to create destination directory fls/script under /ericsson/tor/data/"
            exit 1;
        fi
    else
        return 0
    fi
}

# Function copy file to mounted directory
function copyFile()
{
    if [ -f "$SRC_FILE" ]
    then
        if cp $SRC_FILE $DEST_DIR
        then
            info "$SRC_FILE Source File copied to  $DEST_DIR"
        else
            retryCopyFile;
        fi
    else
        error "Source file $SRC_FILE does not exist"
        exit 1;
    fi
}

# Function retry copy of file
function retryCopyFile()
{
    local MAX_ATTEMPT=3
    local INTERVAL=5
    local COUNTER=0
    local file=/ericsson/tor/data/fls/script/updatePIB.sh

    while [ ! -f $file ] && [ $COUNTER -lt $MAX_ATTEMPT ];
    do
        warn "$file File not found $COUNTER"
        sleep $INTERVAL
        ((COUNTER++))
        cp $SRC_FILE $DEST_DIR
        info "$SRC_FILE Source File copied to  $DEST_DIR"
    done

    if [ ! -f $file ]
    then
        error "$file File not found after $MAX_ATTEMPT attempts"
        exit 1
    fi
}


moveScriptLocation

