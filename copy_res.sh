rm -rf server/src/main/resources/static/
rm -rf server/src/main/resources/templates/
mkdir -p server/src/main/resources/static/css
mkdir -p server/src/main/resources/static/js
mkdir -p server/src/main/resources/static/img
mkdir -p server/src/main/resources/templates/error
cp -f ui/dist/css/* server/src/main/resources/static/css/
cp -f ui/dist/js/* server/src/main/resources/static/js/
cp -f ui/dist/img/* server/src/main/resources/static/img/
cp -f ui/dist/favicon.ico server/src/main/resources/static/
cp -f ui/dist/index.html server/src/main/resources/templates/
cp -f ui/dist/login.html server/src/main/resources/templates/
cp -f ui/dist/403.html server/src/main/resources/templates/error/
cp -f ui/dist/404.html server/src/main/resources/templates/error/
cp -f ui/dist/500.html server/src/main/resources/templates/error/