from pathlib import Path
from string import Template
import re

CPP_DIR = Path("../src/main/cpp/Opmodes")
OUTPUT_DIR = Path("../src/main/generated")

headers = CPP_DIR.rglob("*.hpp")

for header in headers:
    text = header.read_text()
    match = re.search(r'@TeleOp\s*\((.*?)\)', text, re.DOTALL)
    if match:
        match.group(1)
