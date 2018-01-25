curl \
  -X DELETE \
  --header "x-access-token:$1" \
  "http://localhost:8081/user/$1"

echo -ne '\n'