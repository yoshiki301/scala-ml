import argparse
import re

parser = argparse.ArgumentParser()
parser.add_argument("-output")
parser.add_argument("params", nargs="*", help="format => key:value")
args = parser.parse_args()
keys = []
values = []
for str in args.params:
    key = re.findall("(.+):.+", str)
    value = float(re.findall(".+:(.+)", str)[0])
    keys.append(key)
    values.append(value)

print(keys)
print(values)

