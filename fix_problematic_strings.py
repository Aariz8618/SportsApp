#!/usr/bin/env python3
import re

def fix_problematic_strings():
    file_path = 'app/src/main/res/values/strings.xml'
    
    # List of problematic string resource names from the build error
    problematic_strings = [
        'hit', 'no_ball10', 'runners33', 'unfair_play14', 'unfair_play16', 
        'unfair_play19', 'unfair_play24', 'unfair_play25', 'unfair_play26', 
        'unfair_play27', 'unfair_play3', 'unfair_play32', 'unfair_play33', 
        'unfair_play38', 'unfair_play4', 'unfair_play45', 'unfair_play46', 
        'unfair_play52', 'unfair_play53', 'unfair_play59', 'unfair_play60', 
        'unfair_play62', 'unfair_play63', 'unfair_play66', 'unfair_play68', 
        'wicket_keeper11'
    ]
    
    with open(file_path, 'r', encoding='utf-8') as file:
        content = file.read()
    
    print(f"Original file size: {len(content)} characters")
    
    fixes_applied = 0
    
    for string_name in problematic_strings:
        # Find the string entry and clean it
        pattern = rf'(<string name="{re.escape(string_name)}"[^>]*>)(.*?)(</string>)'
        
        def clean_string_content(match):
            nonlocal fixes_applied
            opening_tag = match.group(1)
            content_part = match.group(2)
            closing_tag = match.group(3)
            
            # Clean the content of potential unicode issues
            # Remove any non-printable characters except newlines and tabs
            cleaned_content = ''.join(char for char in content_part 
                                    if char.isprintable() or char in '\n\t\r ')
            
            # Fix common unicode escape issues
            cleaned_content = cleaned_content.replace('\\u', 'u')
            cleaned_content = cleaned_content.replace('\\n', '\n')
            cleaned_content = cleaned_content.replace('\\t', '\t')
            cleaned_content = cleaned_content.replace('\\r', '\r')
            
            # Ensure proper XML escaping
            cleaned_content = cleaned_content.replace('&', '&amp;')
            cleaned_content = cleaned_content.replace('<', '&lt;')
            cleaned_content = cleaned_content.replace('>', '&gt;')
            cleaned_content = cleaned_content.replace('"', '&quot;')
            cleaned_content = cleaned_content.replace("'", '&apos;')
            
            # Fix back the legitimate XML entities
            cleaned_content = cleaned_content.replace('&amp;amp;', '&amp;')
            cleaned_content = cleaned_content.replace('&amp;lt;', '&lt;')
            cleaned_content = cleaned_content.replace('&amp;gt;', '&gt;')
            cleaned_content = cleaned_content.replace('&amp;quot;', '&quot;')
            cleaned_content = cleaned_content.replace('&amp;apos;', '&apos;')
            
            if cleaned_content != content_part:
                fixes_applied += 1
                print(f"Fixed string: {string_name}")
            
            return opening_tag + cleaned_content + closing_tag
        
        content = re.sub(pattern, clean_string_content, content, flags=re.DOTALL)
    
    # Additional cleanup for any remaining problematic patterns
    # Remove any remaining backslash-u sequences that might cause issues
    content = re.sub(r'\\u[0-9a-fA-F]{4}', lambda m: chr(int(m.group()[2:], 16)), content)
    
    with open(file_path, 'w', encoding='utf-8') as file:
        file.write(content)
    
    print(f"Fixed file size: {len(content)} characters")
    print(f"Applied fixes to {fixes_applied} strings")
    print("Fixed problematic string resources")

if __name__ == "__main__":
    fix_problematic_strings()
