import socket
import sys
ip='localhost'
port=8000

mi_socket=socket.socket()
mi_socket.connect((ip,port))

while True:
    mensaje=input()
    if mensaje!='adios':
        mi_socket.send(mensaje)
        respuesta=mi_socket.recv(4000)
        print(respuesta)
    else:
        mi_socket.send(mensaje)
        mi_socket.close()
        sys.exit()



