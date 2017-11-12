import networkx as nx
import matplotlib.pyplot as plt

G = nx.Graph()

G.add_node('C')
G.add_node('D')
G.add_node('E')
G.add_node('F')
G.add_node('G')
G.add_node('H')
G.add_node('L')
G.add_node('M')
G.add_node('Bs')
G.add_node('S')
G.add_node('T')
G.add_node('DB')


print(G.nodes())


G.add_edge('F', 'G', weight=1) 
G.add_edge('F', 'H', weight=3) 
G.add_edge('F', 'DB', weight=3) 

G.add_edge('G', 'F', weight=1) 
G.add_edge('G', 'T', weight=1) 

G.add_edge('L', 'Bs', weight=1) 
G.add_edge('L', 'DB', weight=2) 

G.add_edge('M', 'T', weight=1) 


plt.figure(figsize=(15,15))
pos = nx.spring_layout(G, k=1)
nx.draw_networkx(G, pos)
#nx.draw(G, pos)

plt.axis("off")
plt.show()
