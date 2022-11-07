import socket
import sys
import time

ip='192.168.0.18'
port=4444

mi_socket=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
mi_socket.connect((ip,port))

def cron():
    time.sleep(3)
    print("Listo")
 

while True:
    mensaje=input(str)
    mensaje+='\n'
    mi_socket.send(mensaje.encode())
    if mensaje!="adios\n":
        cron()
        respuesta=mi_socket.recv(4000).decode()
        print(respuesta)
    else:
        mi_socket.close()
        sys.exit()


