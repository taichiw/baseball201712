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


G.add_edge('C', 'D',  weight=67)
G.add_edge('C', 'E',  weight=51)
G.add_edge('C', 'F',  weight=79)
G.add_edge('C', 'G',  weight=81)
G.add_edge('C', 'H',  weight=43)
G.add_edge('C', 'L',  weight=75)
G.add_edge('C', 'M',  weight=82)
G.add_edge('C', 'Bs', weight=73)
G.add_edge('C', 'S',  weight=62)
G.add_edge('C', 'T',  weight=67)
G.add_edge('C', 'DB', weight=68)

G.add_edge('D', 'E',  weight=0)
G.add_edge('D', 'F',  weight=53)
G.add_edge('D', 'G',  weight=88)
G.add_edge('D', 'H',  weight=26)
G.add_edge('D', 'L',  weight=31)
G.add_edge('D', 'M',  weight=66)
G.add_edge('D', 'Bs', weight=59)
G.add_edge('D', 'S',  weight=29)
G.add_edge('D', 'T',  weight=55)
G.add_edge('D', 'DB', weight=25)

G.add_edge('E', 'F',  weight=44)
G.add_edge('E', 'G',  weight=51)
G.add_edge('E', 'H',  weight=29)
G.add_edge('E', 'L',  weight=67)
G.add_edge('E', 'M',  weight=55)
G.add_edge('E', 'Bs', weight=50)
G.add_edge('E', 'S',  weight=9)
G.add_edge('E', 'T',  weight=38)
G.add_edge('E', 'DB', weight=58)

G.add_edge('F', 'G',  weight=93)
G.add_edge('F', 'H',  weight=99)
G.add_edge('F', 'L',  weight=90)
G.add_edge('F', 'M',  weight=79)
G.add_edge('F', 'Bs', weight=52)
G.add_edge('F', 'S',  weight=88)
G.add_edge('F', 'T',  weight=94)
G.add_edge('F', 'DB', weight=100)

G.add_edge('G', 'H',  weight=78)
G.add_edge('G', 'L',  weight=64)
G.add_edge('G', 'M',  weight=84)
G.add_edge('G', 'Bs', weight=52)
G.add_edge('G', 'S',  weight=61)
G.add_edge('G', 'T',  weight=92)
G.add_edge('G', 'DB', weight=67)

G.add_edge('H', 'L',  weight=60)
G.add_edge('H', 'M',  weight=71)
G.add_edge('H', 'Bs', weight=8)
G.add_edge('H', 'S',  weight=54)
G.add_edge('H', 'T',  weight=80)
G.add_edge('H', 'DB', weight=85)

G.add_edge('L', 'M',  weight=78)
G.add_edge('L', 'Bs', weight=92)
G.add_edge('L', 'S',  weight=74)
G.add_edge('L', 'T',  weight=83)
G.add_edge('L', 'DB', weight=98)

G.add_edge('M', 'Bs', weight=76)
G.add_edge('M', 'S',  weight=41)
G.add_edge('M', 'T',  weight=94)
G.add_edge('M', 'DB', weight=62)

G.add_edge('Bs', 'S',  weight=43)
G.add_edge('Bs', 'T',  weight=60)
G.add_edge('Bs', 'DB', weight=57)

G.add_edge('S', 'T',  weight=80)
G.add_edge('S', 'DB', weight=64)

G.add_edge('T', 'DB', weight=65)

print(G.edges(data=True))


plt.figure(figsize=(15,15))
pos = nx.spring_layout(G, k=0.1)
nx.draw_networkx(G, pos)
#nx.draw(G, pos)

plt.axis("off")
plt.show()
