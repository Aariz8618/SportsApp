import re

# Read the strings.xml file
with open('app/src/main/res/values/strings.xml', 'r', encoding='utf-8') as file:
    content = file.read()

# Pattern to find problematic strings with {str} pattern
# This looks for strings that end with {str}" or similar invalid patterns
patterns_to_fix = [
    (r'"{str}"', ''),  # Remove {str} placeholders
    (r'completed/\\\\nThe', r'completed.\\nThe'),  # Fix backslash escapes
    (r'\\n500\|', ''),  # Remove invalid escape sequences
    (r'"{str}', ''),  # Remove incomplete {str} patterns
    (r'{str}"', ''),  # Remove incomplete {str} patterns
]

# Apply the fixes
for pattern, replacement in patterns_to_fix:
    content = re.sub(pattern, replacement, content)

# Write the fixed content back
with open('app/src/main/res/values/strings.xml', 'w', encoding='utf-8') as file:
    file.write(content)

print("Fixed invalid Unicode escape sequences in strings.xml")
