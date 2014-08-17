#include <GL/glut.h>
void display(){
	glClear(GL_COLOR_BUFFER_BIT);//清除颜色缓存

	//glBegin(GL_POLYGON);//开始绘制多边形
	//glBegin(GL_LINES);
	//glBegin(GL_LINE_STRIP);
	glBegin(GL_LINE_LOOP);
	glVertex2f(-0.5, -0.5);
	glVertex2f(-0.5, 0.5);
	glVertex2f(0.5, 0.5);
	glVertex2f(0.5, -0.5);
	glEnd();//结束
	glFlush();//强制执行之前缓存的所有OpenGL命令
}
void display_triangle(){
        glClear(GL_COLOR_BUFFER_BIT);//清除颜色缓存

        //glBegin(GL_POLYGON);//开始绘制多边形
        //glBegin(GL_LINES);
        //glBegin(GL_LINE_STRIP);
        glBegin(GL_TRIANGLES); 
	glVertex2f(-0.5, -0.5); 
	glVertex2f(-0.5, 0.5);
        glVertex2f(0.5, 0.5);
        //glVertex2f(0.5, -0.5);
        glEnd();//结束
        glFlush();//强制执行之前缓存的所有OpenGL命令
}


void display_1(){
	glClear(GL_COLOR_BUFFER_BIT);//清除颜色缓存
	glPointSize(2.0);
	glBegin(GL_POINTS);
	glColor3f(1.0, 1.0, 1.0);
	glVertex2f(-0.5, -0.5);
	glColor3f(1.0, 0.0, 0.0);
	glVertex2f(-0.5, 0.5);
	glColor3f(0.0, 0.0, 1.0);
	glVertex2f(0.5, 0.5);
	glColor3f(0.0, 1.0, 0.0);
	glVertex2f(0.5, -0.5);
	glEnd();//结束
	glFlush();//强制执行之前缓存的所有OpenGL命令
}
void triangle(GLfloat *a, GLfloat *b, GLfloat *c)
{
	glBegin(GL_TRIANGLES);
	glVertex2fv(a);
	glVertex2fv(b);
	glVertex2fv(c);
	glEnd();
}
void divide_triangle(GLfloat *a, GLfloat *b, GLfloat *c, int m)
{
	GLfloat v[3][2];
	int j;
	if(m > 0)
	{
		for(j = 0; j < 2; j++) v[0][j] = (a[j] + b[j]) / 2;
		for(j = 0; j < 2; j++) v[1][j] = (a[j] + c[j]) / 2;
		for(j = 0; j < 2; j++) v[2][j] = (c[j] + b[j]) / 2;
		divide_triangle(a, v[0], v[1], m - 1);
		divide_triangle(v[0], b, v[2], m - 1);
		divide_triangle(v[1], v[2], c, m - 1);
		divide_triangle(v[0], v[1], v[2], m - 1);
	}
	else 
		triangle(a, b, c);
}
void display_triangle_1()
{
	//GLfloat v[3][2] = {-0.5, -0.5, 0.0, 0.3, 0.5, -0.5};
	GLfloat v[3][2] = {-0.7, -0.7, 0.0, 0.9, 0.7, -0.7};
	glClear(GL_COLOR_BUFFER_BIT);
	divide_triangle(v[0], v[1], v[2], 4);
	glFlush();
}
void init()
{
	glClearColor(0.0, 0.0, 0.0, 0.0);//设置清屏色(背景色)，ARGB,这里为黑色
	//glColor3f(1.0, 1.0, 1.0);//设置填充颜色，这里是RGB白色
	//glColor3f(1.0, 1.0, 0.0);
	//glLineWidth(2.0);//线宽
	//glLineStipple(3, 0xcccc);//点划模式
	glPolygonMode(GL_FRONT, GL_LINE);
	glMatrixMode(GL_PROJECTION);//指定矩阵模式
	glLoadIdentity();//将矩阵初始化为单位矩阵
	gluOrtho2D(-1.0, 1.0, -1.0, 1.0);//修改当前矩阵为用户所期望的矩阵 ，这里用的是OpenGL的默认值

}

int main(int argc, char **argv)
{
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
	glutInitWindowSize(500, 500);
	glutInitWindowPosition(0, 0);
	glutCreateWindow("simple");
	//glutDisplayFunc(display);
	//glutDisplayFunc(display_1);
	glutDisplayFunc(display_triangle_1);
	init();
	//glPolygonMode(GL_FRONT, GL_LINE);
	glutMainLoop();
}
