#!/bin/bash

# Anonymization Script for Portfolio Preparation
# This script renames directories, files, and string contents to remove client references.
# Target: CartonPlast -> EnterpriseERP, cp- -> erp-, etc.

# WARNING: This script uses find and sed to perform massive string replacements.
# Make sure you have committed your current work (or have a backup) before running this script.

echo "Starting anonymization process..."

# 1. Rename directories
echo "Renaming directories..."
if [ -d "cartonplastng" ]; then mv cartonplastng erp-frontend; fi
if [ -d "cp-core-ws" ]; then mv cp-core-ws erp-core-ws; fi
if [ -d "cp-config-ws" ]; then mv cp-config-ws erp-config-ws; fi
if [ -d "cp-users-ws" ]; then mv cp-users-ws erp-users-ws; fi

# Function to rename directories and files containing 'cp-' to 'erp-'
rename_files_and_dirs() {
    # Find all directories containing cp-
    find . -depth -type d -name "*cp-*" -execdir bash -c 'mv "$1" "${1//cp-/erp-}"' _ {} \;
    # Find all files containing cp-
    find . -depth -type f -name "*cp-*" -execdir bash -c 'mv "$1" "${1//cp-/erp-}"' _ {} \;
}

echo "Renaming subdirectories and files..."
rename_files_and_dirs

# 2. String Replacement using sed
# We skip the .git and node_modules directories
echo "Replacing strings in files..."

# OS-specific sed handling (macOS requires an empty string argument for -i)
if [[ "$OSTYPE" == "darwin"* ]]; then
  SED_CMD="sed -i ''"
else
  SED_CMD="sed -i"
fi

find . -type f -not -path "*/\.git/*" -not -path "*/node_modules/*" -not -path "*/build/*" -not -path "*/\.gradle/*" -exec $SED_CMD 's/cartonplast/enterprise-erp/g' {} +
find . -type f -not -path "*/\.git/*" -not -path "*/node_modules/*" -not -path "*/build/*" -not -path "*/\.gradle/*" -exec $SED_CMD 's/CartonPlast/EnterpriseERP/g' {} +
find . -type f -not -path "*/\.git/*" -not -path "*/node_modules/*" -not -path "*/build/*" -not -path "*/\.gradle/*" -exec $SED_CMD 's/cp-core/erp-core/g' {} +
find . -type f -not -path "*/\.git/*" -not -path "*/node_modules/*" -not -path "*/build/*" -not -path "*/\.gradle/*" -exec $SED_CMD 's/cp-config/erp-config/g' {} +
find . -type f -not -path "*/\.git/*" -not -path "*/node_modules/*" -not -path "*/build/*" -not -path "*/\.gradle/*" -exec $SED_CMD 's/cp-users/erp-users/g' {} +
find . -type f -not -path "*/\.git/*" -not -path "*/node_modules/*" -not -path "*/build/*" -not -path "*/\.gradle/*" -exec $SED_CMD 's/org.crsoft/com.portfolio/g' {} +

echo "Anonymization complete. Please review the changes using 'git diff' in the sub-repositories."
