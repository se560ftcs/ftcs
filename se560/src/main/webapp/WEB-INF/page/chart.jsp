<html>
<head>
	<style>
		text {
		  font: 24px "Helvetica Neue", Helvetica, Arial, sans-serif;
		  text-anchor: middle;
		  pointer-events: none;
		}
		
		circle {
		  fill: #ccc;
		}
		
		.node:hover circle {
		  fill: orange;
		}
	</style>
</head>

<body>
<script src="http://d3js.org/d3.v3.min.js"></script>
<script type="text/javascript" >

var bleed = 100,
    width = 1200,
    height = 1050;

var pack = d3.layout.pack()
    .sort(null)
    .size([width, height + bleed * 2])
    .padding(2);

var svg = d3.select("body").append("svg")
    .attr("width", width)
    .attr("height", height)
  .append("g")
    .attr("transform", "translate(0," + -bleed + ")");

d3.json("http://localhost:8080/list/cluster", function(error, json) {
  var node = svg.selectAll(".node")
      .data(pack.nodes(flatten(json))
        .filter(function(d) { return !d.children; }))
    .enter().append("g")
      .attr("class", "node")
      .attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });

  node.append("circle")
      .attr("r", function(d) { return d.r; });

  node.append("text")
      .text(function(d) { return d.name; })
      .style("font-size", function(d) { return Math.min(2 * d.r, (2 * d.r - 8) / this.getComputedTextLength() * 24) + "px"; })
      .attr("dy", ".35em");
});

// Returns a flattened hierarchy containing all leaf nodes under the root.
function flatten(root) {
  var nodes = [];

  function recurse(node) {
    if (node.children) node.children.forEach(recurse);
    else nodes.push({name: node.name, value: node.size});
  }

  recurse(root);
  return {children: nodes};
}

d3.select(self.frameElement).style("height", height + "px");

</script>


</body>
</html>