import networkx as nx
import json
import matplotlib.pyplot as plt
import pickle
import os
import networkx.algorithms.community as nxcom
plt.rcParams.update(plt.rcParamsDefault)
plt.rcParams.update({'figure.figsize': (15, 10)})


def process_data():
    if os.path.isfile("nx_graph.pkl"):
        with open("nx_graph.pkl", "rb") as f:
            return pickle.load(f)
    G = nx.Graph()
    id2idx = {}
    idx2id = []
    with open("event.json") as f:
        events_dict = json.load(f)
    for idx, event in enumerate(events_dict["data"]):
        id2idx[event["_id"]] = idx
        idx2id.append(event["_id"])
        G.add_node(idx)
    for idx, event in enumerate(events_dict["data"]):
        for edge in event["related_events"]:
            try:
                v = id2idx[edge["id"]]
            except KeyError:
                continue
            G.add_edge(idx, v, weight=edge["score"])
    with open("nx_graph.pkl", "wb") as f:
        pickle.dump(G, f)
    return G


def set_node_community(G, communities):
    '''Add community to node attributes'''
    for c, v_c in enumerate(communities):
        for v in v_c:
            # Add 1 to save 0 for external edges
            G.nodes[v]['community'] = c + 1


def set_edge_community(G):
    '''Find internal edges and add their community to their attributes'''
    for v, w, in G.edges:
        if G.nodes[v]['community'] == G.nodes[w]['community']:
            # Internal edge, mark with community
            G.edges[v, w]['community'] = G.nodes[v]['community']
        else:
            # External edge, mark as 0
            G.edges[v, w]['community'] = 0


def get_color(i, r_off=1, g_off=1, b_off=1):
    '''Assign a color to a vertex.'''
    r0, g0, b0 = 0, 0, 0
    n = 16
    low, high = 0.1, 0.9
    span = high - low
    r = low + span * (((i + r_off) * 3) % n) / (n - 1)
    g = low + span * (((i + g_off) * 5) % n) / (n - 1)
    b = low + span * (((i + b_off) * 7) % n) / (n - 1)
    return (r, g, b)


def remove_outliers(G):
    return G


def community_detection(G):
    G = remove_outliers(G)
    pos = nx.spring_layout(G, k=0.1)
    plt.rcParams.update(plt.rcParamsDefault)
    plt.rcParams.update({'figure.figsize': (15, 10)})
    # plt.style.use('dark_background')

    # Set node and edge communities
    communities = sorted(
        nxcom.greedy_modularity_communities(G), key=len, reverse=True)
    set_node_community(G, communities)
    set_edge_community(G)

    # Set community color for internal edges
    external = [(v, w)
                for v, w in G.edges if G.edges[v, w]['community'] == 0]
    internal = [(v, w)
                for v, w in G.edges if G.edges[v, w]['community'] > 0]
    internal_color = ["black" for e in internal]
    node_color = [get_color(G.nodes[v]['community'])
                  for v in G.nodes]
    # external edges
    nx.draw_networkx(
        G,
        pos=pos,
        node_size=0,
        edgelist=external,
        edge_color="silver",
        node_color=node_color,
        alpha=0.2,
        with_labels=False)
    # internal edges
    nx.draw_networkx(
        G, pos=pos,
        edgelist=internal,
        edge_color=internal_color,
        node_color=node_color,
        alpha=0.05,
        with_labels=False)
    plt.show()


if __name__ == "__main__":
    G = process_data()
    community_detection(G)
