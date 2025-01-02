#!/bin/bash

if [ "$#" -lt 1 ]; then
    echo "Usage: $0 <file1> <file2> ... <output_name>"
    exit 1
fi

OUTPUT_NAME="compressed_files.tar.gz"

# Allow specifying a custom output name
if [ "${!#}" != "${!#:1}" ]; then
    OUTPUT_NAME="${!#}"
    set -- "${@:1:$#-1}"
fi

TARGET_DIR="core/src/main/java/savetheking/game"

if [ ! -d "$TARGET_DIR" ]; then
    echo "Error: Directory $TARGET_DIR does not exist. Check your project structure."
    exit 1
fi

tar -czvf $OUTPUT_NAME -C "$TARGET_DIR" "$@"
echo "Files compressed into $OUTPUT_NAME"
