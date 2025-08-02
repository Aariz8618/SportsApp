package com.aariz.sportsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.w3c.dom.Document
import org.w3c.dom.Element
import javax.xml.parsers.DocumentBuilderFactory

class LayoutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutId = arguments?.getInt("layoutId") ?: return null
        
        // Create a simple layout to display the XML content
        val view = inflater.inflate(R.layout.fragment_topic_display, container, false)
        
        // Load and parse the XML content
        loadXmlContent(view, layoutId)
        
        return view
    }
    
    private fun loadXmlContent(view: View, layoutId: Int) {
        try {
            val inputStream = resources.openRawResource(layoutId)
            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val document: Document = builder.parse(inputStream)
            
            val titleElement = document.getElementsByTagName("title").item(0) as Element
            val descriptionElement = document.getElementsByTagName("description").item(0) as Element
            
            val titleTextView = view.findViewById<TextView>(R.id.tv_topic_title)
            val descriptionTextView = view.findViewById<TextView>(R.id.tv_topic_description)
            
            titleTextView.text = titleElement.textContent
            descriptionTextView.text = descriptionElement.textContent
            
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle error - maybe show a default message
            val titleTextView = view.findViewById<TextView>(R.id.tv_topic_title)
            val descriptionTextView = view.findViewById<TextView>(R.id.tv_topic_description)
            
            titleTextView.text = "Error loading content"
            descriptionTextView.text = "Unable to load the requested content."
        }
    }
}
