rm -rf ./dist/
npm run build3
rsync -rcvPz dist/ root@192.168.5.138:/home/www/project-tycj/pc/dist