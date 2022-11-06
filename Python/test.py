import socket
import sys
ip='192.168.0.18'
port=4444

mi_socket=socket.socket()
mi_socket.connect((ip,port))

while True:
    mensaje=input(str)
    if mensaje!='adios':
        mi_socket.send(mensaje.encode())
        respuesta=mi_socket.recv(4000).decode()
        print(respuesta)
    else:
        mi_socket.send(mensaje)
        mi_socket.close()
        sys.exit()



