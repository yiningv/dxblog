set -a
. /etc/default/github-webhook

nohup java -jar dxblog.jar & PID=$!
echo $PID > pid.txt