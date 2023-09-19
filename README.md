# EhrmsDashboard

Generate analytics data for child organisations of given departments and save it to a json file.

## Run
1. Add the list of mapIds of departments in `resources/deptList.json`
2. Set the values of `ORG_LIST_PATH`, `METRICS_PATH`, `SPV_URL`, `PORTAL_URL`, `RESPONSE_PATH` in `util/Constants.php`
3. Run the code. The analytics data of all onboarded child organisations of the given departments will be saved in the file given in `RESPONSE_PATH`.
