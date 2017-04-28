#!/usr/bin/env python
# -*- coding: utf-8 -*-
# import json
import graphviz as gv
import Queue
from copy import copy,deepcopy
def growPaths(init,end,M,paths,primePaths,EPC,simplePaths):
	newPaths=[]
	if len(paths[0])==3:
		# print "caminos de long 3:",paths
		for x in paths:
			EPC.append(copy(x))
	for p in paths:
		print p,
		if p[-1]==p[0]  and len(p)>1:
			print "*"
			primePaths.append(copy(p)) 
		else:
			if len(set(M[p[-1]])-set(p[1:]))==0: #no way to grow the path (except closing a cyle)
				print "!"
				primePaths.append(copy(p))
			else:
				print 
				for i in range(len(M)):
					if i in M[p[-1]] and (i not in p or i==p[0]):
						newPaths.append(copy(p))
						newPaths[-1].append(i)
						print "añadiendo ",[x+1 for x in newPaths[-1]]
						simplePaths.append(deepcopy(newPaths[-1])) #simples
	# print newPaths
	print "----------------"
	if(len(newPaths)>0):
		growPaths(init,end,M,newPaths,primePaths,EPC,simplePaths)
def findPrimePAths(init,end,M):
	paths=[]
	EPC=[]
	for i in range(len(M)):
		paths.append([i])
	primePaths__=[]
	simplePaths=[]
	growPaths(init,end,M,paths,primePaths__,EPC,simplePaths)
	
	# print "Simplepaths",simplePaths
	#Ahora debemos considerar los caminos que terminan en * o !
	primePaths=[]
	for i in range(len(primePaths__)):
		isPrime=True
		p=primePaths__[i]
		l1=len(p)
		for j in range(i+1,len(primePaths__)):
			if not isPrime:
				break

			q=primePaths__[j]
			l2=len(q)
			# print q,"vs",p
			if l1<l2:
				for k in range(l2-l1+1):
					# print "\t\tcomparing", q[k:k+l1]," vs ",p
					if q[k:k+l1]==p:
						# print "\t\texitoso"
						isPrime=False
						j=len(primePaths__)+1
						break
		if isPrime:
			primePaths.append(copy(p))
	print "prime Paths:"
	for p in primePaths:
		for x in p:
			print x,
		print 
	print "hay ", len(primePaths), "caminos primos"
	print "EPC:"
	for p in EPC:
		for x in p:
			print x,
		print 
	print "hay ", len(EPC), "pares de aristas"
	return simplePaths
def du(u,simplePaths,defs,uses):
	print "\t\tdu(",u,")"
	for p in simplePaths:
		if u in defs[p[0]]:
			# print "candidato: ",[x+1 for x in p]
			for i in range(1,len(p)):
				if i<len(p)-1 and u in defs[p[i]]:
					break
				if i==len(p)-1 and u in uses[p[i]]:
					# print "CANDIDATO ACEPTADO"
					# print "candidato: ",p 
					print [x+1 for x in p]
					# print [defs[x] for x in p]
					# print [uses[x] for x in p]
					# print p


f=open("prueba1_main.txt","r")
content=f.read()
print content
lines=content.rsplit('\n')
print lines
nNodos=int(lines[0])
init=[int(x) for x in lines[1].rsplit()]
end=[int(x) for x in lines[2].rsplit()]
print "nNodos", nNodos
print "init  ", init
print "end   ", end

g=gv.Digraph(format='svg')
M=[]
#crear nodos en la imagen
for i in range(nNodos):
	# g.node(str(i+1))
	# if i+1 in init:
	if i in init:
		g.node(str(i),style='filled',color='steelblue1')#pruebas
	# if i+1 in end:
	if i in end:
		g.node(str(i),style='filled',color='indianred1')#pruebas
	M.append([])

#crear aristas en el grafo
#crear las listas de acuerdo al archivo
for i in range(nNodos):
	for x in lines[3+i].rsplit():
		g.edge(str(i),x)
		# g.edge(str(i),str(int(x)-1)) #para pruebas
		# M[i].append(int(x)-1)
		M[i].append(int(x))

defs=[]
uses=[]
for i in range(nNodos):
	defs.append(lines[3+nNodos+i].split())
for i in range(nNodos):
	uses.append(lines[3+2*nNodos+i].split())
vars= set([])
for x in defs:
	for y in x:
		vars.add(y)
for x in uses:
	for y in x:
		vars.add(y)	
vars=list(vars)
# print g.source

filename = g.render(filename='g')
print "Archivo guardado en",filename
print "Listas de adyacencia"
for i in range(nNodos):
	print i,M[i]

print "Defs"
for i in range(nNodos):
	print i,defs[i]

print "uses"
for i in range(nNodos):
	print i,uses[i]

print "variables: "
print vars

simplePaths=findPrimePAths(init,end,M) 
# for i in init:
# 	findPath(i,primePaths[0],M)

cont=0
for x in vars:
	# if cont>0:
	# 	break
	# else:
	du(x,simplePaths,defs,uses)
	cont=1
