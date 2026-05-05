#type vertex
#version 330 core

// Input attribute at location 0: vertext position (x,y,z)
layout (location=0) in vec3 aPos;

// Input attribute at location 1: vertext color(r, g, b, a)
layout (location=1) in vec4 aColor;

// Output variable passed to fragment shader
out vec4 fColor;

void main(){
    // pass the color directly to fragment shader
    fColor = aColor;

    // convert vec3 position into vec4 for OpenGL
    // w = 1.0 means this is a posotion not direction
    gl_Position = vec4(aPos, 1.0);
}

#type fragment
#version 330 core

in vec4 fColor;

out vec4 color;

void main(){
    color = fColor;
}