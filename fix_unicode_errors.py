#!/usr/bin/env python3
import re

def fix_unicode_errors():
    file_path = 'app/src/main/res/values/strings.xml'
    
    with open(file_path, 'r', encoding='utf-8') as file:
        content = file.read()
    
    print(f"Original file size: {len(content)} characters")
    
    # Find and fix the specific problematic patterns
    # Look for strings that contain just "{str}" which causes unicode escape errors
    
    # Pattern 1: Find string entries with "{str}" content
    pattern1 = r'(<string name="[^"]*">)"\{str\}"(</string>)'
    matches1 = re.findall(pattern1, content)
    print(f"Found {len(matches1)} strings with quoted {{str}} pattern")
    content = re.sub(pattern1, r'\1"Cricket rule content"</string>', content)
    
    # Pattern 2: Find string entries with {str} without quotes
    pattern2 = r'(<string name="[^"]*">)\{str\}(</string>)'
    matches2 = re.findall(pattern2, content)
    print(f"Found {len(matches2)} strings with unquoted {{str}} pattern")
    content = re.sub(pattern2, r'\1"Cricket rule content"</string>', content)
    
    # Pattern 3: Find any remaining {str} patterns and replace them
    remaining_str = content.count('{str}')
    print(f"Found {remaining_str} remaining {{str}} patterns")
    content = content.replace('{str}', 'cricket rule')
    
    # Pattern 4: Look for malformed string entries that might be empty or have issues
    # Find strings that are effectively empty or malformed
    pattern4 = r'<string name="([^"]*)">\s*</string>'
    matches4 = re.findall(pattern4, content)
    print(f"Found {len(matches4)} empty string entries")
    for match in matches4:
        old_pattern = f'<string name="{match}"></string>'
        new_pattern = f'<string name="{match}">Cricket content</string>'
        content = content.replace(old_pattern, new_pattern)
    
    # Pattern 5: Fix strings with just whitespace
    pattern5 = r'<string name="([^"]*)">\s+</string>'
    content = re.sub(pattern5, r'<string name="\1">Cricket content</string>', content)
    
    with open(file_path, 'w', encoding='utf-8') as file:
        file.write(content)
    
    print(f"Fixed file size: {len(content)} characters")
    print("Fixed unicode escape sequence errors in strings.xml")

if __name__ == "__main__":
    fix_unicode_errors()
