import argparse

def parseTest():
    parser = argparse.ArgumentParser()
    parser.add_argument("--echo")
    parser.add_argument("-r", "--result", action="store_true")
    args = parser.parse_args()

    if args.result:
        print(args.echo)

parseTest()