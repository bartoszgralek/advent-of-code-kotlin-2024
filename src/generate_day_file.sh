#!/bin/bash

# Function to generate a new Kotlin file and corresponding text files
generate_day_file() {
    local day_number=$1
    # Format the day number as a two-digit string
    local day_str=$(printf "%02d" $day_number)

    # Check if the template file exists
    local template_file="DayTemplate.kt"
    if [ ! -f "$template_file" ]; then
        echo -e "\e[31mTemplate file $template_file not found!\e[0m"
        exit 1
    fi

    # Read the template file content
    local template_content
    template_content=$(<"$template_file")

    # Replace placeholders with actual day number
    local file_content="${template_content//DayXX/Day$day_str}"

    # Create a new Kotlin file
    local kotlin_file="Day${day_str}.kt"
    echo "$file_content" > "$kotlin_file"

    # Create empty test and target text files
    local test_file="Day${day_str}_test.txt"
    local target_file="Day${day_str}.txt"
    touch "$test_file" "$target_file"

    # Add the Kotlin file to Git tracking
    git add "$kotlin_file"

    # Output colored messages
    echo -e "\e[32mGenerated $kotlin_file\e[0m"
    echo -e "\e[32mGenerated $test_file and $target_file\e[0m"
    echo -e "\e[34mAdded $kotlin_file to Git tracking\e[0m"
}

# Check if the script receives exactly one argument
if [ "$#" -ne 1 ]; then
    echo -e "\e[31mUsage: $0 <day_number>\e[0m"
    exit 1
fi

# Call the function with the provided day number
generate_day_file "$1"
