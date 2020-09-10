import networkx as nx
import json
import matplotlib.pyplot as plt
import pickle
import os
import networkx.algorithms.community as nxcom
from networkx.algorithms.community.quality import modularity
from networkx.utils.mapped_queue import MappedQueue
import random
from numpy import random as nprand
random.seed(123)
nprand.seed(123)
plt.rcParams.update(plt.rcParamsDefault)
plt.rcParams.update({'figure.figsize': (15, 10)})


def process_data():
    G = nx.Graph()
    id2idx = {}
    idx2id = []
    with open("event.json") as f:
        events_dict = json.load(f)
    for idx, event in enumerate(events_dict["data"]):
        id2idx[event["_id"]] = idx
        idx2id.append(event["_id"])
        events_dict["data"][idx]["_id"] = idx
        G.add_node(idx)
    for idx, event in enumerate(events_dict["data"]):
        for edge in event["related_events"]:
            try:
                v = id2idx[edge["id"]]
            except KeyError:
                continue
            G.add_edge(idx, v, weight=edge["score"])
    return G, events_dict["data"]


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
    low, high = 0, 1
    span = high - low
    r = low + span * (((i + r_off) * 3) % n) / (n - 1)
    g = low + span * (((i + g_off) * 5) % n) / (n - 1)
    b = low + span * (((i + b_off) * 7) % n) / (n - 1)
    return (r, g, b)


def remove_outliers(G):
    for node in list(G.nodes):
        if G.degree[node] == 0:
            G.remove_node(node)
    return G


def community_detection(G, threshold=15):
    G = remove_outliers(G)
    # communities = next(nxcom.girvan_newman(G))
    communities = sorted(
        nxcom.greedy_modularity_communities(G), key=len, reverse=True)
    droped = list(filter(lambda x: len(x) <= threshold, communities))
    communities = list(filter(lambda x: len(x) > threshold, communities))
    print(len(communities))
    for com in droped:
        for node in com:
            G.remove_node(node)
    # Set node and edge communities
    set_node_community(G, communities)
    set_edge_community(G)
    return G, communities


def plot_communities(G, communities):
    pos = nx.spring_layout(G, k=0.1)
    # pos = nx.spectral_layout(G)
    plt.rcParams.update(plt.rcParamsDefault)
    plt.rcParams.update({'figure.figsize': (15, 15)})
    node_color = [get_color(G.nodes[v]['community'])
                  for v in G.nodes]
    # Set community color for internal edges
    external = [(v, w)
                for v, w in G.edges if G.edges[v, w]['community'] == 0]
    internal = [(v, w)
                for v, w in G.edges if G.edges[v, w]['community'] > 0]
    # external edges
    nx.draw_networkx(
        G,
        pos=pos,
        node_size=0,
        edgelist=external,
        edge_color="black",
        node_color=node_color,
        alpha=0.6,
        with_labels=False)
    # internal edges
    nx.draw_networkx(
        G, pos=pos,
        edgelist=internal,
        edge_color="silver",
        node_color=node_color,
        alpha=0.4,
        with_labels=False)
    plt.tight_layout()
    plt.legend()
    plt.savefig("nx_communities.png")
    # plt.show()


def mark_event(events, G, communities):
    final_events = {}
    for id, event in enumerate(events):
        if G.has_node(id):
            entities = []
            event["community"] = G.nodes[id]['community']
            for entity in event["entities"]:
                entities.append(entity["label"])
            event["entities"] = entities
            final_events[id] = event
    print(len(final_events))
    with open("clusterd_events.json", "w") as f:
        json.dump(final_events, f, ensure_ascii=False)


def mark_community(community):
    with open("clusterd_events.json") as f:
        events = json.load(f)
    communities_entities = {}
    for idx, com in enumerate(communities):
        entity_occur = {}
        for id in com:
            for entity in events[str(id)]["entities"]:
                if entity in entity_occur:
                    entity_occur[entity] += 1
                else:
                    entity_occur[entity] = 1
        entity_occur = sorted(entity_occur.items(),
                              key=lambda d: d[1], reverse=True)
        communities_entities[idx+1] = entity_occur[0:20]
    with open("communities_entities.json", "w") as f:
        json.dump(communities_entities, f, ensure_ascii=False)


if __name__ == "__main__":
    G, events = process_data()
    G, communities = community_detection(G)
    mark_event(events, G, communities)
    mark_community(communities)
    plot_communities(G, communities)
