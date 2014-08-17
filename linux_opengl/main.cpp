#include <GL/glut.h>
#include <stdlib.h>
#include <stdio.h>
#include "app.h"
using namespace std;


int main(int argc, char **argv)
{
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_RGB | GLUT_SINGLE);
    glutInitWindowPosition(0, 0);
    glutInitWindowSize(300, 300);

    glutCreateWindow("OpenGl 3D View");

    Application::Init();

    glutDisplayFunc(Application::display);

    glutMainLoop();
    return 0;
}

