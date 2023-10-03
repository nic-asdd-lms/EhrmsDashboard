# iGOT Dashboard Script

Generate analytics data for child organisations of given departments and save it to a json file.

## Run
1. Add the list of `mapIds` of departments in `deptList.json`
2. Set the folowing environment variables:
```
CONFIG=<path to config file>
DEPT_LIST=<path to deptList.json>
OUTPUT=<path to output file>
``` 
2. Run the code. The analytics data of all onboarded child organisations of the given departments will be saved in the file given in `OUTPUT`.
