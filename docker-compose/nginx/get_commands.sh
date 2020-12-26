alias build='docker build --no-cache -t simoncer/nginx .'
alias run='docker run --name nginx-try -p 8044:8044 simoncer/nginx'
alias clean='docker rm nginx-try && docker rmi simoncer/nginx'
alias into='docker exec -it nginx-try bash'
