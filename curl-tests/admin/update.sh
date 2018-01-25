curl \
  -X PUT \
  -d "{\"id\": $1, \"login\":\"$2\",\"password\":\"$3\"}" \
  "http://localhost:8081/user"

echo -ne '\n'