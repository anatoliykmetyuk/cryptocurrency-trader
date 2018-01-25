curl \
  -X POST \
  --header "x-access-token:$1" \
  -d "{\"login\":\"$1\",\"password\":\"$2\"}" \
  "http://localhost:8081/user"

echo -ne '\n'