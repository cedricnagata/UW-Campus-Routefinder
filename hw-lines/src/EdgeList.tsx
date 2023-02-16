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

interface EdgeListProps {
    onChange: (edges: string[]) => any;
}
interface EdgeListState {
    text: string;
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class EdgeList extends Component<EdgeListProps, EdgeListState> {
    constructor (props: any) {
        super(props);
        this.state = {text: ""};
    }

    textChange(event: any) {
        this.setState({
            text: event.target.value,
        })
    }

    parseString(input: String): string[] {
        const edgeArray: string[] = input.split("\n");
        return edgeArray;
    }

    render() {
        return (
            <div id="edge-list">
                Edges <br/>
                <textarea
                    rows={5}
                    cols={30}
                    value={this.state.text}
                    onChange={(event) => {
                        this.textChange(event);
                        console.log('textarea onChange was called');
                        }}
                /> <br/>
                <br>
                    <button onClick={() => {
                        this.props.onChange(this.parseString(this.state.text));
                        console.log('Draw onClick was called');
                    }}>Draw</button>
                </br>
                <br>
                    <button onClick={() => {
                        let empty: string[] = [];
                        this.props.onChange(empty);
                        console.log('Clear onClick was called');
                    }}>Clear</button>
                </br>
            </div>
        );
    }
}

export default EdgeList;
