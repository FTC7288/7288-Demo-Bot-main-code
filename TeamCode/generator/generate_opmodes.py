from pathlib import Path
import re

CPP_DIR = Path("../src/main/cpp/opmodes")
OUTPUT_DIR = Path("../src/main/generated")

headers = CPP_DIR.rglob("*.hpp")

for header in headers:
    print(header)
    text = header.read_text()
    print(text)
    match = re.search(r'@TeleOp\s*\((.*?)\)', text, re.DOTALL)
    if match:
        match.group(1)

