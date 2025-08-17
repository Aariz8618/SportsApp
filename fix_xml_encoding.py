#!/usr/bin/env python3
import re
import xml.etree.ElementTree as ET

def fix_strings_xml():
    file_path = 'app/src/main/res/values/strings.xml'
    
    try:
        # Read the file with UTF-8 encoding
        with open(file_path, 'r', encoding='utf-8') as file:
            content = file.read()
        
        print(f"Original file size: {len(content)} characters")
        
        # Fix common XML encoding issues
        fixes_applied = []
        
        # Replace smart quotes with regular quotes
        if '"' in content or '"' in content:
            content = content.replace('"', '"').replace('"', '"')
            fixes_applied.append("Smart quotes")
        
        # Replace smart apostrophes
        if ''' in content or ''' in content:
            content = content.replace(''', "'").replace(''', "'")
            fixes_applied.append("Smart apostrophes")
        
        # Replace em dashes and en dashes
        if '—' in content or '–' in content:
            content = content.replace('—', '-').replace('–', '-')
            fixes_applied.append("Em/en dashes")
        
        # Replace ellipsis character
        if '…' in content:
            content = content.replace('…', '...')
            fixes_applied.append("Ellipsis")
        
        # Fix any remaining non-ASCII characters that might cause issues
        # Replace with closest ASCII equivalent
        replacements = {
            'á': 'a', 'à': 'a', 'ä': 'a', 'â': 'a', 'ã': 'a', 'å': 'a',
            'é': 'e', 'è': 'e', 'ë': 'e', 'ê': 'e',
            'í': 'i', 'ì': 'i', 'ï': 'i', 'î': 'i',
            'ó': 'o', 'ò': 'o', 'ö': 'o', 'ô': 'o', 'õ': 'o',
            'ú': 'u', 'ù': 'u', 'ü': 'u', 'û': 'u',
            'ñ': 'n', 'ç': 'c',
            '°': ' degrees',
            '£': 'pounds',
            '€': 'euros',
            '®': '(R)',
            '©': '(C)',
            '™': '(TM)'
        }
        
        for char, replacement in replacements.items():
            if char in content:
                content = content.replace(char, replacement)
                fixes_applied.append(f"Character '{char}'")
        
        # Ensure proper XML structure
        if not content.strip().startswith('<?xml'):
            content = '<?xml version="1.0" encoding="utf-8"?>\n' + content
            fixes_applied.append("XML declaration")
        
        # Validate basic XML structure
        try:
            # Try to parse as XML to check for structural issues
            root = ET.fromstring(content)
            print("XML structure is valid")
        except ET.ParseError as e:
            print(f"XML parsing error: {e}")
            # Try to fix common XML issues
            content = re.sub(r'&(?!amp;|lt;|gt;|quot;|apos;)', '&amp;', content)
            fixes_applied.append("Unescaped ampersands")
        
        # Write the fixed content back
        with open(file_path, 'w', encoding='utf-8') as file:
            file.write(content)
        
        print(f"Fixed file size: {len(content)} characters")
        print(f"Applied fixes: {', '.join(fixes_applied) if fixes_applied else 'None needed'}")
        
        return True
        
    except Exception as e:
        print(f"Error fixing strings.xml: {e}")
        return False

if __name__ == "__main__":
    fix_strings_xml()
