import socket
import sys
ip='192.168.0.18'
port=4444

mi_socket=socket.socket()
mi_socket.connect((ip,port))

while True:
    mensaje=input(str)
    mi_socket.send(mensaje.encode())
    if mensaje!="adios":
        respuesta=mi_socket.recv(4000).decode()
        print(respuesta)
    else:
        mi_socket.close()
        sys.exit()


