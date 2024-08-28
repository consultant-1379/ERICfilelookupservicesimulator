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
# $Date: 2017-03-30
# $Author: ehimnay
#
# This script verifies if lock file exists and update the pib.
# The Lock file is an indication that postgres file system is 90% filled and fls should stop using flsdb.
# To use fls. Lock file needs to be removed and the pib should be turn off manually.
###########################################################################

if [ -f /ericsson/tor/data/fls_threshold_action ]; then
    jvmIdentifier=$(/bin/hostname -s)
    /opt/ericsson/PlatformIntegrationBridge/etc/config.py update --app_server_address="$jvmIdentifier":8080 --name="$1" --value="$2" --app_server_identifier="$jvmIdentifier" --scope=GLOBAL
    exit 1
fi
exit 0