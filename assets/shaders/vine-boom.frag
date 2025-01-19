#define HIGHP

uniform sampler2D u_texture;

uniform vec2 u_resolution;
uniform float u_intensity;

varying vec2 v_texCoords;

void main(){
    vec2 c = v_texCoords.xy - 0.5;
    vec2 coords = c.x * u_resolution;

    float offset = sqrt(coords.x * coords.x + coords.y * coords.y) / u_intensity;
    float ang = atan(coords.x, coords.y);

    vec2 from = coords - vec2(offset * cos(ang), offset * sin(ang));
    from /= u_resolution;
    from += 0.5;

    vec4 color = texture2D(u_texture, v_texCoords.xy);
    vec4 colorBlur = texture2D(u_texture, from.xy);

    gl_FragColor = color + colorBlur * 0.5;
}
