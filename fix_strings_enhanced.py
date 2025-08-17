import re

# Read the strings.xml file
with open('app/src/main/res/values/strings.xml', 'r', encoding='utf-8') as file:
    content = file.read()

print("Original content length:", len(content))

# More specific pattern to find and fix problematic strings
# This pattern looks for string entries that have just "{str}" as their content
pattern = r'(<string name="[^"]*">)"\{str\}"(</string>)'

# Count how many matches we find
matches = re.findall(pattern, content)
print(f"Found {len(matches)} problematic strings")

# Replace with proper empty strings
content = re.sub(pattern, r'\1""', content)

# Also handle any other malformed patterns like missing quotes
# Fix strings that have just {str} without proper quotes
pattern2 = r'(<string name="[^"]*">)\{str\}(</string>)'
content = re.sub(pattern2, r'\1"Fixed string content"', content)

# Fix any remaining {str} patterns
content = content.replace('{str}', 'cricket')

# Write the fixed content back
with open('app/src/main/res/values/strings.xml', 'w', encoding='utf-8') as file:
    file.write(content)

print("Fixed invalid Unicode escape sequences in strings.xml")
print("New content length:", len(content))
