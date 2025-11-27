# Tek dosya yükle
curl -X POST -F "file=@/path/to/test.png" http://localhost:8080/api/files/upload

# Çoklu dosya yükle
curl -X POST -F "files=@/path/a.png" -F "files=@/path/b.pdf" http://localhost:8080/api/files/uploads

# Listele
curl http://localhost:8080/api/files

# İndir
curl -OJ http://localhost:8080/api/files/1699999999999_my.pdf

# Sil
curl -X DELETE http://localhost:8080/api/files/1699999999999_my.pdf
