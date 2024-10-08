docker run --rm -d --name seg-web-tp-db ^
-e MYSQL_ROOT_PASSWORD=fuv938S763j -p 3306:3306 ^
-v %USERPROFILE%\seg-web-mysql-db:/var/lib/mysql ^
mysql