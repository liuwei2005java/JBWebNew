package com.jb.unit;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;


/*
 * ���ڰ�һ��ͼƬת��Ϊ12x18��01����
 * 
 * @author soulmachine@gmail.com
 * @version 1.0, 02/04/2010
 */
public class BiImage {
    /** ���ļ���ȡ��Image ���󣬱��ڲ��� */
    //protected Image image;
    
    /** ͼƬ�Ŀ� */
    protected int width;
    
    /** ͼƬ�ĸ� */
    protected int height;
    
    /** ��image�е����ض�ȡ����������ڱ������� */
    protected int pixels[];
    
    /** The constructor */
    public BiImage() {
        
    }
    
    /**
     * �ж�һ��ͼƬ�ļ������͡�
     * ǰ���ǣ���֪���ļ���ͼƬ������������ȡ�ļ�ͷ�������ֽڽ����жϡ�
     * ��Ȼ���Զ�������ֽڻ����ȷ������û��Ҫ����Ϊ��֪��ͼƬ�ˡ�
     * 
     * @param file
     * @return ͼƬ���ͺ�׺
     * @throws IOException 
     */
    
    private static String getImageType(String filePath) throws IOException{
        File f = new File(filePath);
        FileInputStream in = null;
        String type = null;
        byte[] bytes = { 0, 0 }; // ���ڴ���ļ�ͷ�����ֽ�

        in = new FileInputStream(f);

        in.read(bytes, 0, 2);

        if (((bytes[0] & 0xFF) == 0x47) && ((bytes[1] & 0xFF) == 0x49)) { // GIF
            type = "gif";
        } else if (((bytes[0] & 0xFF) == 0x89) && ((bytes[1] & 0xFF) == 0x50)) { // PNG
            type = "png";
        } else if (((bytes[0] & 0xFF) == 0xFF) && ((bytes[1] & 0xFF) == 0xD8)) { // JPG
            type = "jpg";
        } else if (((bytes[0] & 0xFF) == 0x42) && ((bytes[1] & 0xFF) == 0x4D)) { // BMP
            type = "bmp";
        } else { // not supported type
            // System.out.println("not supported type!");
        }

        in.close();

        return type;
    }
    
    
    /**
     * �ж�һ��TYPE_INT_ARGB��ɫ�ǿ�����ɫ���ǿ�����ɫ
     * 
     * @param pixel һ�� TYPE_INT_ARGB��ɫ
     * @return ��Ӧ�ĺ�ɫ���ɫ
     */
    private static int convertToBlackWhite(int pixel) {
        int result = 0;

        //int alpha = (pixel >> 24) & 0xff; // not used
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        
        result = 0xff000000; // ��������ɫ��ΪȫF���� -1
        
        int tmp = red * red + green * green + blue * blue;
        if(tmp > 3*128*128){ // ���ڣ����ǰ�ɫ
            result += 0x00ffffff;
        } else { // �Ǻ�ɫ
            
        }
        
        return result;
    }
    /**
     * �Ӵ����ļ���ȡͼƬ
     * 
     * @param imageFile �ļ�·��
     * @return BufferedImage����ʧ��Ϊnull
     * @throws IOException 
     */
    public static BufferedImage readImageFromFile(BufferedInputStream in) throws IOException{
        BufferedImage bi;
        
        // ��ȡĳ��ͼƬ��ʽ��reader����
        Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("jpg");
        ImageReader reader = (ImageReader)readers.next();
        // Ϊ��reader������������Դ
        ImageInputStream iis = ImageIO.createImageInputStream(in);
        reader.setInput(iis);
        
        // ����ͼƬ����
        bi = reader.read(0);
        
        readers = null;
        reader = null;
        iis = null;
        
        return bi;
    }
    
    /**
     * ��ͼƬд������ļ�
     * 
     * @param imgFile �ļ�·��
     * @param bi BufferedImage ����
     * @return ��
     */
    public static void  writeImageToFile(String imgFile, BufferedImage bi)
    throws IOException {
        // дͼƬ��������
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(
                imgFile.substring(imgFile.lastIndexOf('.') + 1));
        ImageWriter writer = (ImageWriter) writers.next();
        // �������Դ
        File f = new File(imgFile);
        ImageOutputStream ios;

        ios = ImageIO.createImageOutputStream(f);
        writer.setOutput(ios);
        // д�뵽����
        writer.write(bi);
    }
    
   
    
    /** 
     * ��ʼ������
     * 
     * @param imageFile �ļ�·��
     * @return ��
     * @throws IOException 
     * @throws IOException 
     * @exception ��
     */
    public void initialize(BufferedInputStream in) throws IOException{
        BufferedImage bi = readImageFromFile(in);
        
        // �õ���͸�
        width = bi.getWidth(null);
        height = bi.getHeight(null);
        
        // ��ȡ����
        pixels = new int[width * height];
        bi.getRGB(0, 0, width, height, pixels, 0, width);
        
        bi = null;
    }
    
    /**
     * ��ͼƬת��Ϊ�ڰ�ͼƬ
     * 
     * @param imgFile ����ļ�·��
     * @return
     */
    public BufferedImage monochrome(String imgFile) { 
        int newPixels[] = new int[width * height];
        for(int i = 0; i < width * height; i++) {
            newPixels[i] = convertToBlackWhite(pixels[i]);
        }
        
        BufferedImage bi = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        bi.setRGB(0, 0, width, height, newPixels, 0, width);
        newPixels = null;
        
        try {
            BiImage.writeImageToFile(imgFile, bi);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return bi;
    }
    
    /**
     * �ж�һ��ͼƬ�Ƿ��Ǻڰ�ͼƬ
     * 
     * @param imgFile
     * @return �ǣ�����true���ҶȻ��ɫͼƬ������false
     */
    private static boolean isMonochrome() {
        BufferedImage bi = null;
        boolean result = false;
        int w = 0, h = 0;
        int i = 0, j = 0;
        
//        try {
//            bi = readImageFromFile();
//            
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        
        w = bi.getWidth();
        h = bi.getHeight();
        int count = 0; //�ڰ����ظ���
        int n = 0; // �Ǻڰ׸���
        for(j = 0; j < h; j++)
            for(i = 0; i < w; i++) {
                int rgb = bi.getRGB(i, j);
                rgb &= 0x00FFFFFF;
                if((rgb != 0x00FFFFFF) && (rgb != 0)){ // �Ȳ��ǰ�ɫҲ���Ǻ�ɫ
                    n++;
                    break;
                }
                else {
                    count ++;
                }
            }
        System.out.println(count);
        System.out.println(n);
        if((i == w) && (j == h)) {
            result = true;
        } else {
            result = false;
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        
        BiImage bi = new BiImage();
//        try {
//            bi.initialize();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        
        bi.monochrome("c:\\heibai1.jpg"); // �ڰ׻������������
        // �Ӵ��̶�ȡ�����ɵ��ļ������ÿ�����أ��Ƿ��Ǻڰ���ɫ
        //System.out.println(BiImage.isMonochrome(args[1])); // ����false�����ʧ�ܣ���
    }
}
