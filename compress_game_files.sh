#!/bin/bash

# Define the directory where the game files are stored relative to the project root
game_dir="core/src/main/java/savetheking/game"

# Define the output directory for the compressed archive
output_dir="."

# Check if at least one filename is provided
if [ "$#" -lt 1 ]; then
  echo "Usage: $0 filename1 [filename2 ... filenameN]"
  echo "Example: $0 Bishop Board Controller"
  exit 1
fi

# Create the name of the archive
archive_name="game_files_$(date +%Y%m%d_%H%M%S).zip"

# Create a temporary directory to store the files for compression
temp_dir=$(mktemp -d)

# Loop through the provided filenames
for filename in "$@"; do
  filepath="$game_dir/$filename.java"
  if [ -f "$filepath" ]; then
    # Copy the file to the temporary directory
    cp "$filepath" "$temp_dir/"
  else
    echo "Warning: File '$filepath' does not exist and will be skipped."
  fi
done

# Compress the files into a zip archive
zip -j "$output_dir/$archive_name" "$temp_dir"/*

# Remove the temporary directory
rm -rf "$temp_dir"

# Inform the user
echo "Files have been compressed into $output_dir/$archive_name"
