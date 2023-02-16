/*
 * Copyright (C) 2022 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import React, {Component} from 'react';

import "./App.css";
import InputBuildings from "./InputBuildings";
import Map from "./Map";

interface AppState {
    start: string;
    end: string;
    edges: any[]
}

class App extends Component<{}, AppState> {
    constructor(props: any) {
        super(props);
        this.state = {
            start: "",
            end: "",
            edges: [],
        };
    }

    setStart(startInput: string) {
        this.setState( {
            start: startInput,
        })
    }

    setEnd(endInput: string) {
        this.setState( {
            end: endInput,
        })
    }

    setEdges(edgeList: any[]) {
        this.setState({
            edges: edgeList,
        })
    }

    findPath = async () => {
        try {
            if (this.state.start == "" || this.state.end == "") {
                let emptyEdges: any[] = [];
                this.setEdges(emptyEdges);
            } else {
                let response = await fetch("http://localhost:4567/find-path?start=" +
                    this.state.start + "&end=" + this.state.end);

                if (!response.ok) {
                    alert("The status is wrong! Expected: 200, Was: " + response.status);
                    return;
                }

                let pathPromise = response.json();
                let pathText = await pathPromise;

                let pathArray = pathText['path'];
                let edgeArray: any[] = [];

                for (let i = 0; i < pathArray.length; i++) {

                    let start = pathArray[i]['start'];
                    let end = pathArray[i]['end'];

                    let x1 = start['x'];
                    let y1 = start['y'];
                    let x2 = end['x'];
                    let y2 = end['y'];

                    edgeArray[i] = [x1, y1, x2, y2];
                }

                this.setEdges(edgeArray);
            }
        } catch (e) {
            console.log(e);
        }
    }

    render() {
        void this.findPath()
        return (
            <div>
                <h1 style={{
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center"
                }} id="app-title">UW Campus Path Finder!</h1>
                <div>
                    <InputBuildings
                        onChange={(value1, value2) => {
                            this.setStart(value1)
                            this.setEnd(value2)
                        }}
                    />
                </div>
                <div>
                    <Map edges = {this.state.edges}/>
                </div>
            </div>
        );
    }
}

export default App;
