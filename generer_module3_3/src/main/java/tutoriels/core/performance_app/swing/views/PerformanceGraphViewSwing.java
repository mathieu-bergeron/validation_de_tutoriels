package tutoriels.core.performance_app.swing.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import ca.ntro.core.system.debug.T;
import tutoriels.core.Constants;
import tutoriels.core.performance_app.TestParameters;
import tutoriels.core.performance_app.views.PerformanceGraphView;

public class PerformanceGraphViewSwing extends JPanel implements PerformanceGraphView {
	private static final long serialVersionUID = -751761072644940648L;
	
	private class DataPoint{
		private String categoryName;
		private int inputSize;
		private double executionTimeSeconds;
		
		public DataPoint(String categoryName, int inputSize, double executionTimeSeconds) {
			T.call(this);
			
			this.categoryName = categoryName;
			this.inputSize = inputSize;
			this.executionTimeSeconds = executionTimeSeconds;
		}
		public int getInputSize() {
			return inputSize;
		}
		public double getExecutionTimeSeconds() {
			return executionTimeSeconds;
		}
		public void setExecutionTimeSeconds(double executionTimeSeconds) {
			this.executionTimeSeconds = executionTimeSeconds;
		}
		public String getCategoryName() {
			return categoryName;
		}
	}
	
	
	
	private List<DataPoint> dataPoints = Collections.synchronizedList(new ArrayList<>());
	private int maxInputSize = (int) 0;
	private double maxExecutionTimeSeconds = 0;

	private Timer animationTimer;
	
	private TestParameters testParemeters;
	
	private List<Color> colors = new ArrayList<>();
	private int nextColor = 0;

	private Map<String, Color> categoryColors = new HashMap<>();
	private Map<String, Integer> categoryOffsets = new HashMap<>();
	private Map<String, Boolean> displayedCategories = new HashMap<>();
	private int nextOffset = 0;
	
	private int baseMargin = 40;
	

	public PerformanceGraphViewSwing() {
		super();
		
		colors.add(Color.BLUE);
		colors.add(Color.GREEN);
		colors.add(Color.ORANGE);
		colors.add(Color.RED);
	}
	
	@Override
	public void paint(Graphics g) {
	  Graphics2D graphic2d = (Graphics2D) g;

	  graphic2d.clearRect(0, 0, getWidth(), getHeight());
	  displayedCategories.clear();
	  
	  int graphHeight = getHeight() - 2*baseMargin;
	  int graphWidth = getWidth() - 3*baseMargin;
	  
	  int nbTicks = 10;
	  int timeStep = graphHeight / nbTicks;
	  for(int i = 0; i < nbTicks-1; i++) {
		  int tickX = 10;
		  int tickY = graphHeight - i * timeStep - baseMargin;
		  double timeTick = ((double) i)/ ((double)nbTicks) * testParemeters.getEstimatedMaxExecutionTime();

		  graphic2d.drawString(String.format("%.2f", timeTick), tickX, tickY);
	  }
	  
	  int categoryX = baseMargin + graphWidth / 2;
	  
	  synchronized(dataPoints) {
		  
		  if(dataPoints.size() > 0) {
			  String firstCategory = dataPoints.get(0).getCategoryName();

			  for(DataPoint dataPoint : dataPoints) {
				  graphic2d.setColor(categoryColors.get(dataPoint.getCategoryName()));
				  
				  if(!displayedCategories.containsKey(dataPoint.getCategoryName())) {
					  displayedCategories.put(dataPoint.getCategoryName(), true);

					  graphic2d.fillRect(categoryX,baseMargin-10,10,10);
					  categoryX += 20;
					  graphic2d.setColor(Color.BLACK);
					  graphic2d.drawString(dataPoint.getCategoryName(), categoryX, baseMargin);
					  graphic2d.setColor(categoryColors.get(dataPoint.getCategoryName()));
					  categoryX += 100;
				  }

				  double _x = ((double)dataPoint.getInputSize()-testParemeters.getMinInputSize()) / ((double)testParemeters.getMaxInputSize()) * graphWidth;
				  int x = (int) _x;
				  x += categoryOffsets.get(dataPoint.getCategoryName());
				  x += 2*baseMargin;

				  int height = (int) (graphHeight * dataPoint.getExecutionTimeSeconds() / testParemeters.getEstimatedMaxExecutionTime());
				  if(height <= 0) {
					  height = 1;
				  }

				  int y = graphHeight - height - baseMargin;
				  int width = 10;

				  graphic2d.fillRect(x,y,width,height);
				  
				  if(dataPoint.getCategoryName().equals(firstCategory)) {
					  int tickX = x;
					  int tickY = graphHeight - 10;

					  AffineTransform old = graphic2d.getTransform();
					  graphic2d.setColor(Color.BLACK);
					  graphic2d.rotate(Math.toRadians(45), tickX, tickY);
					  graphic2d.drawString(String.format("%d", dataPoint.getInputSize()), tickX, tickY);
					  graphic2d.setTransform(old);
				  }
			  }
		  }
	  }
	}
	
	public void step01FetchCommands() {
		T.call(this);
	}

	@Override
	public void step02InstallEventListeners() {
		T.call(this);
	}

	@Override
	public int addDataPoint(String categoryName, int inputSize) {
		T.call(this);

		//adjustMaxInputSize(inputSize);
		int id = dataPoints.size();
		DataPoint dataPoint = new DataPoint(categoryName, inputSize, 0);
		PerformanceGraphViewSwing.this.dataPoints.add(dataPoint);

		System.out.print(String.format("%s   taille:%s", 
			                           	 categoryName, 
			                           	 inputSize));

		if(!categoryColors.containsKey(categoryName)) {
			categoryColors.put(categoryName, colors.get(nextColor));
			categoryOffsets.put(categoryName, nextOffset);
			nextColor++;
			nextOffset += 10;
		}
		
		if(testParemeters.getAnimate()) {
			if(animationTimer != null) {
				animationTimer.cancel();
			}

			animationTimer = new Timer();
			animationTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					T.call(this);

					double executionTimeSeconds = dataPoint.getExecutionTimeSeconds() + ((double)Constants.PERFORMANCE_ANIMATION_RESOLUTION_MILISECONDS) / 1E3 * 0.75;
					dataPoint.setExecutionTimeSeconds(executionTimeSeconds);
					javax.swing.SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							PerformanceGraphViewSwing.this.repaint();
						}
					});
				}
				
			}, 0, Constants.PERFORMANCE_ANIMATION_RESOLUTION_MILISECONDS);
		}
		

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				PerformanceGraphViewSwing.this.repaint();
			}
		});

		return id;
	}

	private void adjustMaxExecutionTime(double executionTimeSeconds) {
		T.call(this);

		if(executionTimeSeconds > maxExecutionTimeSeconds) {
			maxExecutionTimeSeconds = executionTimeSeconds;
		}
	}

	private void adjustMaxInputSize(int inputSize) {
		T.call(this);

		if(inputSize > maxInputSize) {
			maxInputSize = inputSize;
		}
	}

	@Override
	public void setFinalExecutionTime(int id, double executionTimeSeconds) {
		T.call(this);

		if(testParemeters.getAnimate()) {
			if(animationTimer != null) {
				animationTimer.cancel();
			}
		}
		
		DataPoint dataPoint = dataPoints.get(id);
		
		//adjustMaxExecutionTime(executionTimeSeconds);
		
		dataPoint.setExecutionTimeSeconds(executionTimeSeconds);
		
		System.out.println(String.format("   temps:%s", executionTimeSeconds));

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				PerformanceGraphViewSwing.this.repaint();
			}
		});
		
	}

	@Override
	public void setTestParameters(TestParameters parameters) {
		T.call(this);

		this.testParemeters = parameters;
	}
}
